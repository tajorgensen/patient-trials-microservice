-- Check if procedure exists and drop it if needed
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'CalculateAverageDosage')
BEGIN
DROP PROCEDURE CalculateAverageDosage;
END

GO

-- Create the updated procedure with TRIAL_ID parameter
CREATE PROCEDURE CalculateAverageDosage
    @TRIAL_ID INT = NULL -- Optional parameter, NULL will return results for all trials
AS
BEGIN
    -- Set nocount on to prevent extra result sets
    SET NOCOUNT ON;

    -- Calculate average dosage for each drug, grouped by measurement type
    -- Now filtered by TRIAL_ID if provided and including TRIAL.NAME
SELECT
    T.ID AS TRIAL_ID,
    T.NAME AS TRIAL_NAME,
    D.ID AS DRUG_ID,
    D.NAME AS DRUG_NAME,
    PTD.DOSAGE_MEASUREMENT_TYPE,
    AVG(PTD.DOSAGE) AS AVERAGE_DOSAGE,
    COUNT(PT.ID) AS PATIENT_COUNT
FROM
    DRUG D
        INNER JOIN
    PATIENT_TRIAL_DRUG PTD ON D.ID = PTD.DRUG_ID
        INNER JOIN
    PATIENT_TRIAL PT ON PT.ID = PTD.PATIENT_TRIAL_ID
        INNER JOIN
    TRIAL T ON T.ID = PT.TRIAL_ID
WHERE
    (@TRIAL_ID IS NULL OR PT.TRIAL_ID = @TRIAL_ID)
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

GO