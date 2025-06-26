-- Create PostgreSQL trigger function for withdrawing patients on severe adverse events
CREATE OR REPLACE FUNCTION dbo.withdraw_patient_on_severe_event()
RETURNS TRIGGER AS $$
BEGIN
    -- Update PATIENT_TRIAL.TREATMENT_STATUS to 'WITHDRAWN'
    -- when a new SEVERE adverse event is recorded
    UPDATE dbo.PATIENT_TRIAL
    SET TREATMENT_STATUS = 'WITHDRAWN'
    WHERE PATIENT_ID = NEW.PATIENT_ID
      AND TRIAL_ID = NEW.TRIAL_ID
      AND NEW.EVENT_SEVERITY = 'SEVERE';

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
DROP TRIGGER IF EXISTS tr_adverse_events_withdraw_patient ON dbo.ADVERSE_EVENT;

CREATE TRIGGER tr_adverse_events_withdraw_patient
    AFTER INSERT ON dbo.ADVERSE_EVENT
    FOR EACH ROW
    EXECUTE FUNCTION dbo.withdraw_patient_on_severe_event();