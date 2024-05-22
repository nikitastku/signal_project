package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {

    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void alertAction() {
        decoratedAlert.alertAction();
        System.out.println("This is a high priority alert.");
    }
}
