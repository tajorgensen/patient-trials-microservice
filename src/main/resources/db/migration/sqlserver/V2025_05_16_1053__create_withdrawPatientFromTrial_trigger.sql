-- Check if trigger exists and drop it if needed
IF EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_ADVERSE_EVENTS_WithdrawPatient')
BEGIN
DROP TRIGGER TR_ADVERSE_EVENTS_WithdrawPatient;
END

GO

-- Create the trigger
CREATE TRIGGER TR_ADVERSE_EVENTS_WithdrawPatient
    ON ADVERSE_EVENT
    AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Update PATIENT_TRIAL.TREATMENT_STATUS to 'WITHDRAWN'
    -- when a new SEVERE adverse event is recorded
UPDATE PT
SET PT.TREATMENT_STATUS = 'WITHDRAWN'
    FROM PATIENT_TRIAL PT
    INNER JOIN inserted I ON PT.PATIENT_ID = I.PATIENT_ID AND PT.TRIAL_ID = I.TRIAL_ID
WHERE I.EVENT_SEVERITY = 'SEVERE';

END;

GO