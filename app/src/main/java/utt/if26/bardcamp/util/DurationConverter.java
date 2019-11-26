package utt.if26.bardcamp.util;

public class DurationConverter {
    /**
     * Converti une duree de milliseconde en un type heure:mnute:seconde
     * */
    public String milliSecondsToTimer(long time) {
        int heure   = (int) time / (1000 * 60 * 60);
        int minute  = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
        int seconde = (int)((time % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        String finalString="";

        if (heure > 0) {
            finalString += heure + ":";
            if (minute < 10){
                finalString+="0";
            }
        }
        finalString+=minute + ":";

        if (seconde < 10) {
            finalString += "0" + seconde;
        } else {
            finalString += "" + seconde;
        }
        return finalString;
    }

    public int percentageToMillis(int percentage, int totalDuration) {
        return (int) (percentage * 0.01 * totalDuration);
    }
}
