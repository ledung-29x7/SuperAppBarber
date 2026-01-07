CREATE DATABASE super_app_barber;
USE uper_app_barber;
-- USER
CREATE TYPE user_status AS ENUM ('BLOCKED', 'ACTIVE');

-- STAFF
CREATE TYPE staff_role AS ENUM ('OWNER', 'STAFF');

-- BOOKING
CREATE TYPE booking_status AS ENUM (
  'PENDING',
  'CONFIRMED',
  'DONE',
  'CANCELLED',
  'NO_SHOW'
);

-- SUBSCRIPTION
CREATE TYPE subscription_status AS ENUM (
  'ACTIVE',
  'EXPIRED',
  'CANCELLED'
);

-- PAYMENT
CREATE TYPE payment_status AS ENUM (
  'PENDING',
  'SUCCESS',
  'FAILED'
);

-- PROMOTION
CREATE TYPE discount_type AS ENUM (
  'PERCENT',
  'FIXED'
);

CREATE TABLE users (
  user_id UUID PRIMARY KEY,
  phone VARCHAR(20) UNIQUE NOT NULL,
  email VARCHAR(100),
  password VARCHAR(255),
  status user_status DEFAULT 'ACTIVE',
  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP
);

ALTER TABLE users ADD COLUMN role VARCHAR(20);
ALTER TABLE users
ALTER COLUMN status TYPE VARCHAR(20);



CREATE TABLE salons (
  salon_id UUID PRIMARY KEY,
  salon_name VARCHAR(255) NOT NULL,
  address TEXT,
  phone VARCHAR(20),
  owner_id UUID NOT NULL,

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_salon_owner
    FOREIGN KEY (owner_id)
    REFERENCES users(user_id)
);

CREATE TABLE salon_settings (
  salon_id UUID PRIMARY KEY,

  open_time TIME,
  close_time TIME,
  booking_interval INT DEFAULT 30,
  allow_auto_booking BOOLEAN DEFAULT true,
  cancel_before_minutes INT DEFAULT 60,

  CONSTRAINT fk_settings_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
    ON DELETE CASCADE
);

CREATE TABLE staff (
  staff_id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  salon_id UUID NOT NULL,

  name VARCHAR(100),
  role staff_role DEFAULT 'STAFF',
  active BOOLEAN DEFAULT true,

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_staff_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),

  CONSTRAINT fk_staff_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
);

CREATE TABLE staff_working_hours (
  id BIGSERIAL PRIMARY KEY,
  staff_id UUID NOT NULL,

  day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),

  start_time TIME NOT NULL,
  end_time TIME NOT NULL,

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_swh_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff(staff_id)
    ON DELETE CASCADE
);

CREATE TABLE day_off (
  id BIGSERIAL PRIMARY KEY,
  staff_id UUID,
  salon_id UUID NOT NULL,

  start_date DATE NOT NULL,
  end_date DATE NOT NULL,

  reason VARCHAR(255),

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_dayoff_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff(staff_id),

  CONSTRAINT fk_dayoff_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
);

CREATE TABLE services (
  service_id BIGSERIAL PRIMARY KEY,
  salon_id UUID NOT NULL,

  name VARCHAR(150) NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  duration_minutes INT NOT NULL,

  active BOOLEAN DEFAULT true,
  deleted_at TIMESTAMP,

  CONSTRAINT fk_service_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
);

CREATE TABLE staff_services (
  staff_id UUID NOT NULL,
  service_id BIGINT NOT NULL,

  PRIMARY KEY (staff_id, service_id),

  CONSTRAINT fk_ss_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff(staff_id)
    ON DELETE CASCADE,

  CONSTRAINT fk_ss_service
    FOREIGN KEY (service_id)
    REFERENCES services(service_id)
    ON DELETE CASCADE
);

CREATE TABLE customers (
  customer_id UUID PRIMARY KEY,
  salon_id UUID NOT NULL,

  name VARCHAR(100),
  phone VARCHAR(20),
  note TEXT,

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_customer_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
);

CREATE TABLE bookings (
  booking_id UUID PRIMARY KEY,

  salon_id UUID NOT NULL,
  customer_id UUID NOT NULL,
  staff_id UUID,

  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,

  status booking_status DEFAULT 'PENDING',
  note TEXT,

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_booking_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id),

  CONSTRAINT fk_booking_customer
    FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id),

  CONSTRAINT fk_booking_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff(staff_id)
);

CREATE TABLE booking_services (
  booking_id UUID NOT NULL,
  service_id BIGINT NOT NULL,

  PRIMARY KEY (booking_id, service_id),

  CONSTRAINT fk_bs_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(booking_id)
    ON DELETE CASCADE,

  CONSTRAINT fk_bs_service
    FOREIGN KEY (service_id)
    REFERENCES services(service_id)
);


CREATE TABLE plans (
  plan_id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  price DECIMAL(12,2) NOT NULL,

  max_staff INT,
  max_bookings INT,

  features JSONB
);

CREATE TABLE subscriptions (
  subscription_id BIGSERIAL PRIMARY KEY,

  salon_id UUID NOT NULL,
  plan_id BIGINT NOT NULL,

  start_date DATE NOT NULL,
  end_date DATE NOT NULL,

  status subscription_status DEFAULT 'ACTIVE',

  created_at TIMESTAMP DEFAULT now(),

  CONSTRAINT fk_sub_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id),

  CONSTRAINT fk_sub_plan
    FOREIGN KEY (plan_id)
    REFERENCES plans(plan_id)
);

-- chỉ 1 subcription được active/salon
CREATE UNIQUE INDEX uniq_active_subscription
ON subscriptions(salon_id)
WHERE status = 'ACTIVE';

CREATE TABLE payments (
  payment_id BIGSERIAL PRIMARY KEY,

  subscription_id BIGINT NOT NULL,

  amount DECIMAL(12,2) NOT NULL,
  method VARCHAR(30),
  provider VARCHAR(50),
  transaction_id VARCHAR(100),

  status payment_status DEFAULT 'PENDING',

  created_at TIMESTAMP DEFAULT now(),
  paid_at TIMESTAMP,

  CONSTRAINT fk_payment_subscription
    FOREIGN KEY (subscription_id)
    REFERENCES subscriptions(subscription_id)
);

CREATE TABLE promotions (
  promotion_id BIGSERIAL PRIMARY KEY,

  salon_id UUID NOT NULL,

  name VARCHAR(150) NOT NULL,
  description TEXT,

  discount_type discount_type,
  discount_value DECIMAL(10,2),

  start_date DATE,
  end_date DATE,

  active BOOLEAN DEFAULT true,
  deleted_at TIMESTAMP,

  created_at TIMESTAMP DEFAULT now(),

  CONSTRAINT fk_promo_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id)
);

CREATE TABLE promotion_services (
  promotion_id BIGINT NOT NULL,
  service_id BIGINT NOT NULL,

  PRIMARY KEY (promotion_id, service_id),

  CONSTRAINT fk_ps_promo
    FOREIGN KEY (promotion_id)
    REFERENCES promotions(promotion_id)
    ON DELETE CASCADE,

  CONSTRAINT fk_ps_service
    FOREIGN KEY (service_id)
    REFERENCES services(service_id)
    ON DELETE CASCADE
);

CREATE TABLE reviews (
  review_id BIGSERIAL PRIMARY KEY,

  salon_id UUID NOT NULL,
  staff_id UUID,
  customer_id UUID NOT NULL,

  rating INT CHECK (rating BETWEEN 1 AND 5),
  comment TEXT,

  created_at TIMESTAMP DEFAULT now(),
  deleted_at TIMESTAMP,

  CONSTRAINT fk_review_salon
    FOREIGN KEY (salon_id)
    REFERENCES salons(salon_id),

  CONSTRAINT fk_review_staff
    FOREIGN KEY (staff_id)
    REFERENCES staff(staff_id),

  CONSTRAINT fk_review_customer
    FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id)
);

CREATE INDEX idx_staff_salon ON staff(salon_id);
CREATE INDEX idx_service_salon ON services(salon_id);
CREATE INDEX idx_customer_phone ON customers(phone);

CREATE INDEX idx_booking_salon_time
ON bookings(salon_id, start_time);

CREATE INDEX idx_booking_staff_time
ON bookings(staff_id, start_time, end_time);

CREATE INDEX idx_subscription_salon
ON subscriptions(salon_id);


CREATE TABLE slot_holds (
    id BIGSERIAL PRIMARY KEY,

    salon_id UUID NOT NULL,
    staff_id UUID NOT NULL,

    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

    holder_key VARCHAR(100) NOT NULL,
    -- userId nếu login, phone nếu guest

    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);

