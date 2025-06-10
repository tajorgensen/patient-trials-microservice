-- Utilized AI (Claude.ai) to create some Seed data for Patient Trial database --

-- PATIENT seed data
INSERT INTO dbo.PATIENT (NAME, DATE_OF_BIRTH, GENDER, MEDICAL_HISTORY, CREATED_AT, UPDATED_AT)
VALUES ('John Smith', '1980-05-15', 'M', 'Hypertension, Type 2 Diabetes', GETDATE(), GETDATE()),
       ('Sarah Johnson', '1975-08-21', 'F', 'Asthma', GETDATE(), GETDATE()),
       ('Michael Brown', '1990-03-10', 'M', 'None', GETDATE(), GETDATE()),
       ('Emily Davis', '1988-11-30', 'F', 'Migraine', GETDATE(), GETDATE()),
       ('Robert Wilson', '1965-04-25', 'M', 'Coronary Artery Disease, Hyperlipidemia', GETDATE(), GETDATE()),
       ('Jennifer Martinez', '1982-09-12', 'F', 'Rheumatoid Arthritis', GETDATE(), GETDATE()),
       ('David Thompson', '1972-07-08', 'M', 'GERD', GETDATE(), GETDATE()),
       ('Jessica White', '1995-01-18', 'F', 'None', GETDATE(), GETDATE()),
       ('Christopher Lee', '1978-06-22', 'M', 'Depression, Anxiety', GETDATE(), GETDATE()),
       ('Amanda Garcia', '1992-12-05', 'F', 'Hypothyroidism', GETDATE(), GETDATE()),
       ('Daniel Rodriguez', '1969-10-14', 'M', 'Type 1 Diabetes', GETDATE(), GETDATE()),
       ('Elizabeth Hernandez', '1985-02-27', 'F', 'Fibromyalgia', GETDATE(), GETDATE()),
       ('Matthew Lopez', '1973-03-19', 'M', 'Mild Asthma, Seasonal Allergies', GETDATE(), GETDATE()),
       ('Stephanie Scott', '1989-08-11', 'F', 'Polycystic Ovary Syndrome', GETDATE(), GETDATE()),
       ('Andrew Mitchell', '1977-05-29', 'M', 'Hypertension', GETDATE(), GETDATE()),
       ('Nicole Turner', '1993-04-16', 'F', 'None', GETDATE(), GETDATE()),
       ('Thomas Parker', '1970-11-24', 'M', 'Osteoarthritis', GETDATE(), GETDATE()),
       ('Rebecca Adams', '1986-07-31', 'F', 'Migraines, Depression', GETDATE(), GETDATE()),
       ('Kevin Campbell', '1974-09-09', 'M', 'High Cholesterol', GETDATE(), GETDATE()),
       ('Laura Evans', '1991-01-08', 'F', 'Irritable Bowel Syndrome', GETDATE(), GETDATE());

-- DRUG seed data
INSERT INTO dbo.DRUG (NAME, CHEMICAL_FORMULA, MANUFACTURER, CREATED_AT, UPDATED_AT)
VALUES ('Metformin', 'C4H11N5', 'Novartis', GETDATE(), GETDATE()),
       ('Lisinopril', 'C21H31N3O5', 'Merck', GETDATE(), GETDATE()),
       ('Albuterol', 'C13H21NO2', 'GlaxoSmithKline', GETDATE(), GETDATE()),
       ('Simvastatin', 'C25H38O5', 'Pfizer', GETDATE(), GETDATE()),
       ('Omeprazole', 'C17H19N3O3S', 'AstraZeneca', GETDATE(), GETDATE()),
       ('Levothyroxine', 'C15H11I4NO4', 'Abbott', GETDATE(), GETDATE()),
       ('Amlodipine', 'C20H25ClN2O5', 'Bayer', GETDATE(), GETDATE()),
       ('Sertraline', 'C17H17Cl2N', 'Teva', GETDATE(), GETDATE()),
       ('Gabapentin', 'C9H17NO2', 'Sandoz', GETDATE(), GETDATE()),
       ('Ibuprofen', 'C13H18O2', 'Johnson & Johnson', GETDATE(), GETDATE()),
       ('Atorvastatin', 'C33H35FN2O5', 'Pfizer', GETDATE(), GETDATE()),
       ('Fluoxetine', 'C17H18F3NO', 'Eli Lilly', GETDATE(), GETDATE()),
       ('Losartan', 'C22H23ClN6O', 'Merck', GETDATE(), GETDATE()),
       ('Azithromycin', 'C38H72N2O12', 'Pfizer', GETDATE(), GETDATE()),
       ('Prednisone', 'C21H26O5', 'Novartis', GETDATE(), GETDATE());

-- TRIAL seed data
INSERT INTO dbo.TRIAL (NAME, START_DATE, END_DATE, PROTOCOL_DESCRIPTION, CREATED_AT, UPDATED_AT)
VALUES ('Diabetes Prevention Study', '2024-01-10', '2025-07-10',
        'Double-blind RCT testing efficacy of new oral antidiabetic agent', GETDATE(), GETDATE()),
       ('Hypertension Management Trial', '2024-03-15', '2025-09-15',
        'Comparative effectiveness of combination therapy vs. monotherapy', GETDATE(), GETDATE()),
       ('Asthma Control Program', '2024-02-20', '2026-02-20', 'Long-term efficacy study of bronchodilator combinations',
        GETDATE(), GETDATE()),
       ('Cholesterol Reduction Initiative', '2024-04-05', '2025-10-05',
        'Evaluation of novel statin therapy at varying dosages', GETDATE(), GETDATE()),
       ('Arthritis Pain Management', '2024-05-12', '2026-05-12',
        'Assessment of new anti-inflammatory for rheumatoid arthritis', GETDATE(), GETDATE()),
       ('Depression Treatment Comparison', '2024-01-25', '2025-07-25',
        'Comparing efficacy of SSRI vs new peptide-based therapy', GETDATE(), GETDATE()),
       ('Fibromyalgia Symptom Relief', '2024-06-08', '2026-06-08',
        'Testing combination therapy approach for pain management', GETDATE(), GETDATE()),
       ('GERD Treatment Optimization', '2024-03-30', '2025-09-30',
        'Evaluating PPI dosing schedules for symptom management', GETDATE(), GETDATE());

-- PATIENT_TRIAL seed data
INSERT INTO dbo.PATIENT_TRIAL (PATIENT_ID, TRIAL_ID, ENROLLMENT_DATE, TREATMENT_STATUS, CREATED_AT, UPDATED_AT)
VALUES (1, 1, '2024-01-15', 'ACTIVE', GETDATE(), GETDATE()),
       (5, 1, '2024-01-17', 'ACTIVE', GETDATE(), GETDATE()),
       (11, 1, '2024-01-20', 'ACTIVE', GETDATE(), GETDATE()),
       (15, 1, '2024-01-22', 'WITHDRAWN', GETDATE(), GETDATE()),

       (1, 2, '2024-03-18', 'ACTIVE', GETDATE(), GETDATE()),
       (5, 2, '2024-03-20', 'ACTIVE', GETDATE(), GETDATE()),
       (15, 2, '2024-03-22', 'ACTIVE', GETDATE(), GETDATE()),
       (19, 2, '2024-03-25', 'COMPLETED', GETDATE(), GETDATE()),

       (2, 3, '2024-02-25', 'ACTIVE', GETDATE(), GETDATE()),
       (3, 3, '2024-02-27', 'WITHDRAWN', GETDATE(), GETDATE()),
       (13, 3, '2024-03-01', 'ACTIVE', GETDATE(), GETDATE()),

       (5, 4, '2024-04-10', 'ACTIVE', GETDATE(), GETDATE()),
       (11, 4, '2024-04-12', 'ACTIVE', GETDATE(), GETDATE()),
       (15, 4, '2024-04-15', 'ACTIVE', GETDATE(), GETDATE()),
       (19, 4, '2024-04-18', 'COMPLETED', GETDATE(), GETDATE()),

       (6, 5, '2024-05-15', 'ACTIVE', GETDATE(), GETDATE()),
       (12, 5, '2024-05-18', 'ACTIVE', GETDATE(), GETDATE()),
       (17, 5, '2024-05-20', 'ACTIVE', GETDATE(), GETDATE()),
       (18, 5, '2024-05-22', 'WITHDRAWN', GETDATE(), GETDATE()),

       (9, 6, '2024-01-30', 'ACTIVE', GETDATE(), GETDATE()),
       (18, 6, '2024-02-02', 'ACTIVE', GETDATE(), GETDATE()),

       (6, 7, '2024-06-10', 'ACTIVE', GETDATE(), GETDATE()),
       (12, 7, '2024-06-12', 'ACTIVE', GETDATE(), GETDATE()),
       (18, 7, '2024-06-15', 'ACTIVE', GETDATE(), GETDATE()),

       (7, 8, '2024-04-05', 'ACTIVE', GETDATE(), GETDATE()),
       (10, 8, '2024-04-08', 'ACTIVE', GETDATE(), GETDATE()),
       (20, 8, '2024-04-10', 'WITHDRAWN', GETDATE(), GETDATE());

-- PATIENT_TRIAL_DRUG seed data
INSERT INTO dbo.PATIENT_TRIAL_DRUG (PATIENT_TRIAL_ID, DRUG_ID, DOSAGE, DOSAGE_MEASUREMENT_TYPE, CREATED_AT, UPDATED_AT)
VALUES
    -- Diabetes Trial Drugs
    (1, 1, 500, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (1, 11, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (2, 1, 1000, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (3, 1, 750, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (4, 1, 500, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- Hypertension Trial Drugs
    (5, 2, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (5, 7, 5, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (6, 2, 20, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (6, 7, 5, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (7, 2, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (8, 13, 50, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- Asthma Trial Drugs
    (9, 3, 100, 'MICROGRAMS', GETDATE(), GETDATE()),
    (10, 3, 100, 'MICROGRAMS', GETDATE(), GETDATE()),
    (11, 3, 200, 'MICROGRAMS', GETDATE(), GETDATE()),

    -- Cholesterol Trial Drugs
    (12, 4, 20, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (13, 4, 40, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (14, 11, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (15, 11, 20, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- Arthritis Trial Drugs
    (16, 10, 800, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (16, 15, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (17, 10, 600, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (17, 15, 5, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (18, 10, 800, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (19, 10, 600, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- Depression Trial Drugs
    (20, 8, 50, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (21, 12, 20, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- Fibromyalgia Trial Drugs
    (22, 9, 300, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (22, 12, 10, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (23, 9, 600, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (24, 9, 300, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (24, 8, 25, 'MILLIGRAMS', GETDATE(), GETDATE()),

    -- GERD Trial Drugs
    (25, 5, 20, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (26, 5, 40, 'MILLIGRAMS', GETDATE(), GETDATE()),
    (27, 5, 20, 'MILLIGRAMS', GETDATE(), GETDATE());

-- ADVERSE_EVENT seed data
INSERT INTO dbo.ADVERSE_EVENT (PATIENT_ID, TRIAL_ID, EVENT_DESCRIPTION, EVENT_SEVERITY, DATE_OF_OCCURRENCE, CREATED_AT,
                               UPDATED_AT)
VALUES (1, 1, 'Mild nausea', 'MILD', '2024-01-30', GETDATE(), GETDATE()),
       (5, 1, 'Dizziness upon standing', 'MODERATE', '2024-02-05', GETDATE(), GETDATE()),
       (15, 1, 'Severe gastrointestinal distress', 'SEVERE', '2024-02-10', GETDATE(), GETDATE()),
       (15, 2, 'Dry cough', 'MILD', '2024-04-02', GETDATE(), GETDATE()),
       (5, 2, 'Ankle swelling', 'MODERATE', '2024-04-15', GETDATE(), GETDATE()),
       (2, 3, 'Throat irritation', 'MILD', '2024-03-10', GETDATE(), GETDATE()),
       (13, 3, 'Palpitations', 'MODERATE', '2024-03-22', GETDATE(), GETDATE()),
       (5, 4, 'Muscle pain', 'MODERATE', '2024-05-03', GETDATE(), GETDATE()),
       (19, 4, 'Memory issues', 'MILD', '2024-05-10', GETDATE(), GETDATE()),
       (6, 5, 'Stomach pain', 'MODERATE', '2024-06-01', GETDATE(), GETDATE()),
       (18, 5, 'Rash', 'SEVERE', '2024-06-05', GETDATE(), GETDATE()),
       (9, 6, 'Insomnia', 'MODERATE', '2024-02-15', GETDATE(), GETDATE()),
       (18, 6, 'Headache', 'MILD', '2024-02-20', GETDATE(), GETDATE()),
       (12, 7, 'Drowsiness', 'MILD', '2024-06-25', GETDATE(), GETDATE()),
       (18, 7, 'Dizziness', 'MODERATE', '2024-06-30', GETDATE(), GETDATE()),
       (7, 8, 'Constipation', 'MILD', '2024-04-20', GETDATE(), GETDATE()),
       (20, 8, 'Severe abdominal pain', 'SEVERE', '2024-04-25', GETDATE(), GETDATE());