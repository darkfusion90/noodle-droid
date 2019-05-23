package com.darkfusion.gaurav.noodledroid;

enum MouseMessageType {
    BUTTON_PRESS_LEFT {
        @Override
        public String typeToString() {
            return "LBD";
        }
    },
    BUTTON_PRESS_RIGHT {
        @Override
        public String typeToString() {
            return "RBD";
        }
    },
    BUTTON_RELEASE_LEFT {
        @Override
        public String typeToString() {
            return "LBU";
        }
    },
    BUTTON_RELEASE_RIGHT {
        @Override
        public String typeToString() {
            return "RBU";
        }
    },
    DRAG_MOUSE {
        @Override
        public String typeToString() {
            return "MDR";
        }
    },
    MOVE_MOUSE {
        @Override
        public String typeToString() {
            return "MMV";
        }
    },
    SINGLE_CLICK {
        @Override
        public String typeToString() {
            return "MSC";
        }
    };

    public abstract String typeToString();
}
