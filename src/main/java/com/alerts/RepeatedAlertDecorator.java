package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {

    public RepeatedAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void alertAction() {
        decoratedAlert.alertAction();
        System.out.println("This alert will be repeated.");
    }
}
