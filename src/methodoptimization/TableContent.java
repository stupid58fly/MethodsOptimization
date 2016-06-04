/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodoptimization;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author ilyab
 */
public class TableContent {
        private Long steps;
        private Double x1;
        private Double x2;
        private Double func;
        private Double power;
        private Double speed;
        private static NumberFormat fd;
        private static NumberFormat fl;
        
        private static void initNF() {
            fd = NumberFormat.getNumberInstance();
            fl = NumberFormat.getNumberInstance();
            
            fl.setParseIntegerOnly(true);
            setFractionDigits(7);
            fd.setRoundingMode(RoundingMode.FLOOR);
        }
        
        public TableContent(long steps, double x1, double x2, double func, double power, double speed) {
            this.steps = steps;
            this.x1 = x1;
            this.x2 = x2;
            this.func = func;
            this.power = power;
            this.speed = speed;
            if (null == fd) initNF();
        }

        /**
         * @return the steps
         */
        public String getSteps() {
            return fl.format(steps);
        }

        /**
         * @param steps the steps to set
         */
        public void setSteps(long steps) {
            this.steps = steps;
        }

        /**
         * @return the x1
         */
        public String getX1() {
            if (x1.isNaN()) return x1.toString();
            else return fd.format(x1);
        }

        /**
         * @param x1 the x1 to set
         */
        public void setX1(double x1) {
            this.x1 = x1;
        }

        /**
         * @return the x2
         */
        public String getX2() {
            if (x2.isNaN()) return x2.toString();
            else return fd.format(x2);
        }

        /**
         * @param x2 the x2 to set
         */
        public void setX2(double x2) {
            this.x2 = x2;
        }

        /**
         * @return the func
         */
        public String getFunc() {
            if (func.isNaN()) return func.toString();
            else return fd.format(func);
        }

        /**
         * @param func the func to set
         */
        public void setFunc(double func) {
            this.func = func;
        }

        /**
         * @return the power
         */
        public String getPower() {
            if (power.isNaN()) return power.toString();
            else return fd.format(power);
        }

        /**
         * @param power the power to set
         */
        public void setPower(double power) {
            this.power = power;
        }

        /**
         * @return the speed
         */
        public String getSpeed() {
            if (speed.isNaN()) return speed.toString();
            else return fd.format(speed);
        }

        /**
         * @param speed the speed to set
         */
        public void setSpeed(double speed) {
            this.speed = speed;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getSteps()).append('\t');
            sb.append(getX1()).append('\t');
            sb.append(getX2()).append('\t');
            sb.append(getFunc()).append('\t');
            sb.append(getPower()).append('\t');
            sb.append(getSpeed()).append('\n');
            
            return sb.toString();
        }
        
        public static void setFractionDigits(final Integer amo) {
            if (null == fd) initNF();
            fd.setMaximumFractionDigits(amo);
            fd.setMinimumFractionDigits(amo);
        }
        
        public static Integer getFractionDigits() {
            if (null != fd)
                return fd.getMaximumFractionDigits();
            return 7;
        }
    }
