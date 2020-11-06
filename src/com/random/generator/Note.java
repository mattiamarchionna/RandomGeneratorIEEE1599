package com.random.generator;

enum BritishNoteValue {
    SEMIBREVE, MINIM, CROTCHET, QUAVER, SEMIQUAVER, DEMISEMIQUAVER, HEMIDEMISEMIQUAVER, SEMIHEMIDEMISEMIQUAVER, DEMISEMIHEMIDEMISEMIQUAVER
}

enum AmericanNoteValue {
    WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTYSECOND, SIXTYFOURTH, HUNDREDTWENTYEIGHTH, TWOHUNDREDFIFTYSIXTH
}

enum PitchEnum {
    A, B, C, D, E, F, G, UNDEFINED
}

enum AccidentalEnum {
    SHARP, FLAT, NATURAL, DOUBLESHARP, DOUBLEFLAT, UNDEFINED
}

// ------------------------------------------------------------------------
class NoteRest {

    protected int numerator;
    protected int denominator;

    public NoteRest() {
        numerator = 1;
        denominator = 1;
    }

    public NoteRest(int n, int expd) {
        numerator = Math.abs(n);
        expd = Math.max(0, expd);
        expd = Math.min(expd, 8);
        denominator = (int) (Math.pow(2, expd)); // pow returns a double
    }

    public NoteRest(int expd) {
        numerator = 1;
        expd = Math.max(0, expd);
        expd = Math.min(expd, 8);
        denominator = (int) (Math.pow(2, expd)); // pow returns a double
    }

    public NoteRest(BritishNoteValue value) {
        numerator = 1;
        denominator = (int) (Math.pow(2, value.ordinal()));
    }

    public NoteRest(AmericanNoteValue value) {
        numerator = 1;
        denominator = (int) (Math.pow(2, value.ordinal()));
    }

    @Override
    public String toString() {
        return (numerator + "/" + denominator);
    }

    public void halveValue() {
        denominator *= 2;
    }

    public void doubleValue() {
        if (denominator > 1)
            denominator /= 2;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public double getNumericDuration() {
        return (double) numerator / denominator;
    }
}

// ------------------------------------------------------------------------
public class Note extends NoteRest {

    public PitchEnum pitch;
    public int octave;
    public AccidentalEnum accidental;

    public Note() {
        pitch = PitchEnum.UNDEFINED;
        octave = -1;
        accidental = AccidentalEnum.UNDEFINED;
        numerator = 0; // se nella classe NoteRest fosse stato dichiarato private, non si sarebbe potuto fare
        denominator = 1;
    }

    public Note(PitchEnum pitch, int octave, AccidentalEnum accidental, int numerator, int denominatorExp) {
        this.pitch = pitch;
        this.octave = octave;
        this.accidental = accidental;
        this.numerator = Math.abs(numerator);
        denominatorExp = Math.max(0, denominatorExp);
        denominatorExp = Math.min(denominatorExp, 8);
        this.denominator = (int) (Math.pow(2, denominatorExp));
    }

    @Override
    public String toString() {
        String stringAccidental = "";
        if (pitch == PitchEnum.UNDEFINED || octave < 0)
            return "Undefined";
        switch (accidental) {
            case SHARP:
                stringAccidental = "#";
                break;
            case FLAT:
                stringAccidental = "b";
                break;
            case DOUBLESHARP:
                stringAccidental = "x";
                break;
            case DOUBLEFLAT:
                stringAccidental = "bb";
        }
        return (pitch + stringAccidental + octave + " [" + this.getNumerator() + "/" + this.getDenominator() + "]");
    }

    @Override
    public Note clone() {
        Note n = new Note();
        n.pitch = this.pitch;
        n.octave = this.octave;
        n.accidental = this.accidental;
        n.numerator = this.numerator;
        n.denominator = this.denominator;
        return n;
    }

    public int convertToMidiPitch() {
        int result;

        result = 12 * octave;
        switch (pitch) {
            case D:
                result += 2;
                break;
            case E:
                result += 4;
                break;
            case F:
                result += 5;
                break;
            case G:
                result += 7;
                break;
            case A:
                result += 9;
                break;
            case B:
                result += 11;
                break;
            case UNDEFINED:
                return -1;
        }
        switch (accidental) {
            case SHARP:
                result += 1;
                break;
            case FLAT:
                result -= 1;
                break;
            case DOUBLESHARP:
                result += 2;
                break;
            case DOUBLEFLAT:
                result -= 2;
                break;
        }

        if (result < 0 || result > 127)
            return -1;

        return result;
    }

    public Note returnHighest(Note noteToCompare) {
        return (this.convertToMidiPitch() >= noteToCompare.convertToMidiPitch()) ? this : noteToCompare;
    }

    public void transposeGrades(int grades) {
        if (grades == 0)
            return;
        int enumIndex = pitch.ordinal();
        if (grades > 0)
            for (int i = 0; i < grades; i++) {
                enumIndex++;
                if (enumIndex > 6)
                    enumIndex = 0;
                if (enumIndex == 2)
                    octave++;
            }
        else
            for (int i = 0; i < grades; i++) {
                enumIndex--;
                if (enumIndex < 0)
                    enumIndex = 6;
                if (enumIndex == 1)
                    octave--;
            }
        pitch = PitchEnum.values()[enumIndex];
    }

    /*public static void main(String[] args) {
        NoteRest r = new NoteRest(AmericanNoteValue.HALF);
        System.out.println("La durata della pausa r e' " + r.toString());
        Note n = new Note(PitchEnum.C, 4, AccidentalEnum.NATURAL, 1, 2);
        System.out.println("La descrizione completa della nota n e' " + n.toString());
        n.halveValue();
        n.halveValue();
        System.out.println("Dopo due dimezzamenti del valore, la descrizione completa della nota n e' " + n.toString());
        System.out.println();

        // Assegnamenti
        r = n;
        // n = r; non sarebbe ammissibile
        n.numerator = 3;
        System.out.println("Dopo l'assegnamento, la descrizione della pausa r e' " + r.toString());
        System.out.println();
        // attenzione: r è diventata a tutti gli effetti un oggetto della sottoclasse nota, 
        // tanto che la funzione toString() è quella della sottoclasse

        Note n2 = n.clone(); // metodo con override esplicito
        System.out.println("Prima n e' " + n.toString() + " e n2 e' " + n2.toString());
        n2.octave = 3;
        n2.numerator = 5;
        n2.accidental = AccidentalEnum.FLAT;
        System.out.println("Ora n e' " + n.toString() + " e n2 e' " + n2.toString());
    } */

}
