package net.pistonmaster.pistonclient.structure.modules;

import com.lukflug.panelstudio.settings.ColorSetting;
import com.lukflug.panelstudio.settings.KeybindSetting;
import com.lukflug.panelstudio.settings.NumberSetting;
import com.lukflug.panelstudio.settings.Toggleable;
import net.pistonmaster.pistonclient.panel.PistonModule;
import net.pistonmaster.pistonclient.structure.ModuleManager;

import java.awt.*;

public class HUDModule extends PistonModule {
    public NumberSetting scrollSpeed = new NumberSetting() {
        double value = 0;

        @Override
        public double getNumber() {
            return value;
        }

        @Override
        public double getMaximumValue() {
            return 3;
        }        @Override
        public void setNumber(double value) {
            this.value = value;
        }

        @Override
        public double getMinimumValue() {
            return 1;
        }

        @Override
        public int getPrecision() {
            return 1;
        }


    };

    public NumberSetting animationSpeed = new NumberSetting() {
        double value = 0;

        @Override
        public double getNumber() {
            return value;
        }

        @Override
        public double getMaximumValue() {
            return 3;
        }        @Override
        public void setNumber(double value) {
            this.value = value;
        }

        @Override
        public double getMinimumValue() {
            return 1;
        }

        @Override
        public int getPrecision() {
            return 1;
        }


    };

    public ColorSetting activeColor = new ColorSetting() {
        private Color color;
        private boolean rainbow;

        @Override
        public Color getValue() {
            return color;
        }

        @Override
        public Color getColor() {
            return color;
        }        @Override
        public void setValue(Color value) {
            this.color = value;
        }

        @Override
        public boolean getRainbow() {
            return rainbow;
        }



        @Override
        public void setRainbow(boolean rainbow) {
            this.rainbow = rainbow;
        }
    };

    public ColorSetting inactiveColor = new ColorSetting() {
        private Color color;
        private boolean rainbow;

        @Override
        public Color getValue() {
            return color;
        }

        @Override
        public Color getColor() {
            return color;
        }        @Override
        public void setValue(Color value) {
            this.color = value;
        }

        @Override
        public boolean getRainbow() {
            return rainbow;
        }



        @Override
        public void setRainbow(boolean rainbow) {
            this.rainbow = rainbow;
        }
    };

    public ColorSetting backgroundColor = new ColorSetting() {
        private Color color;
        private boolean rainbow;

        @Override
        public Color getValue() {
            return color;
        }

        @Override
        public Color getColor() {
            return color;
        }        @Override
        public void setValue(Color value) {
            this.color = value;
        }

        @Override
        public boolean getRainbow() {
            return rainbow;
        }



        @Override
        public void setRainbow(boolean rainbow) {
            this.rainbow = rainbow;
        }
    };

    public ColorSetting outlineColor = new ColorSetting() {
        private Color color;
        private boolean rainbow;

        @Override
        public Color getValue() {
            return color;
        }

        @Override
        public Color getColor() {
            return color;
        }        @Override
        public void setValue(Color value) {
            this.color = value;
        }

        @Override
        public boolean getRainbow() {
            return rainbow;
        }



        @Override
        public void setRainbow(boolean rainbow) {
            this.rainbow = rainbow;
        }
    };

    public ColorSetting fontColor = new ColorSetting() {
        private Color color;
        private boolean rainbow;

        @Override
        public Color getValue() {
            return color;
        }

        @Override
        public Color getColor() {
            return color;
        }        @Override
        public void setValue(Color value) {
            this.color = value;
        }

        @Override
        public boolean getRainbow() {
            return rainbow;
        }



        @Override
        public void setRainbow(boolean rainbow) {
            this.rainbow = rainbow;
        }
    };

    public NumberSetting opacity = new NumberSetting() {
        double value = 0;

        @Override
        public double getNumber() {
            return value;
        }

        @Override
        public double getMaximumValue() {
            return 100;
        }        @Override
        public void setNumber(double value) {
            this.value = value;
        }

        @Override
        public double getMinimumValue() {
            return 1;
        }

        @Override
        public int getPrecision() {
            return 1;
        }


    };

    public Toggleable rgbhsbToggle = new Toggleable() {
        @Override
        public void toggle() {

        }

        @Override
        public boolean isOn() {
            return false;
        }
    };

    public HUDModule() {
        super(ModuleManager.Module.HUD);
    }

    @Override
    public void toggle() {

    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public KeybindSetting getKeybind() {
        return null;
    }
}
