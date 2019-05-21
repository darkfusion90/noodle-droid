package com.darkfusion.gaurav.noodledroid;

class Heartbeat implements Runnable {
    private CommunicationHandler communicationHandler;

    Heartbeat(CommunicationHandler communicationHandler){
        this.communicationHandler = communicationHandler;
    }

    @Override
    public void run() {
        this.communicationHandler.sendHeartbeat();
    }
}
