ALTER TABLE user_ ADD COLUMN
    account_status ENUM('ACTIVATED','DEACTIVATED') DEFAULT 'ACTIVATED';