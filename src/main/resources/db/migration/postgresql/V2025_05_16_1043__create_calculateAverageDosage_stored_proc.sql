-- Create the PostgreSQL function equivalent to the SQL Server stored procedure
CREATE OR REPLACE FUNCTION dbo.CalculateAverageDosage(trial_id_param INTEGER DEFAULT NULL)
RETURNS TABLE (
    trial_id INTEGER,
    trial_name VARCHAR(255),
    drug_id INTEGER,
    drug_name VARCHAR(255),
    dosage_measurement_type VARCHAR(25),
    average_dosage NUMERIC,
    patient_count BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        T.ID::INTEGER AS trial_id,
        T.NAME AS trial_name,
        D.ID::INTEGER AS drug_id,
        D.NAME AS drug_name,
        PTD.DOSAGE_MEASUREMENT_TYPE,
        AVG(PTD.DOSAGE) AS average_dosage,
        COUNT(DISTINCT PT.ID) AS patient_count
    FROM
        dbo.DRUG D
        INNER JOIN dbo.PATIENT_TRIAL_DRUG PTD ON D.ID = PTD.DRUG_ID
        INNER JOIN dbo.PATIENT_TRIAL PT ON PT.ID = PTD.PATIENT_TRIAL_ID
        INNER JOIN dbo.TRIAL T ON T.ID = PT.TRIAL_ID
    WHERE
        (trial_id_param IS NULL OR PT.TRIAL_ID = trial_id_param)
    GROUP BY
        T.ID,
        T.NAME,
        D.ID,
        D.NAME,
        PTD.DOSAGE_MEASUREMENT_TYPE
    ORDER BY
        T.NAME,
        D.NAME,
        PTD.DOSAGE_MEASUREMENT_TYPE;
END;
$$ LANGUAGE plpgsql;