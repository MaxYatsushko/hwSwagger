-- liquibase formatted sql

-- changeset mIatsushko:1
CREATE INDEX student_name_index ON Student (name);
CREATE INDEX faculty_name_color_index ON Faculty (name, color);
