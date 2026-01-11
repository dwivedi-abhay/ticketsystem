-- V2__fix_payment_status.sql
ALTER TABLE payment RENAME COLUMN status TO payment_status;
