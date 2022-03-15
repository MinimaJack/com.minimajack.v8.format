package com.minimajack.v8.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SerializedOutputStream extends ByteArrayOutputStream {

    private boolean isEmpty = true;
    private boolean lastBracket = false;
    private static final byte SYMBOL_COMA = 0x2C;
    private static final byte SYMBOL_CR = 0x0D;
    private static final byte SYMBOL_LF = 0x0A;
    private static final byte SYMBOL_OPEN_BKT = 0x7B;
    private static final byte SYMBOL_CLOSE_BKT = 0x7D;

    public void putComa() {
        this.write(SYMBOL_COMA);
        lastBracket = false;
    }

    public void putOpenBracket() {
        if (isEmpty) {
            isEmpty = false;
        } else {
            this.write(SYMBOL_CR);
            this.write(SYMBOL_LF);
        }

        this.write(SYMBOL_OPEN_BKT);
        lastBracket = false;
    }

    public void putCloseBracket() {
        if(lastBracket){
            this.write(SYMBOL_CR);
            this.write(SYMBOL_LF);
        }
        this.write(SYMBOL_CLOSE_BKT);
        lastBracket = true;
    }

    @Override
    public void writeBytes(byte[] b) {
        super.writeBytes(b);
        lastBracket = false;
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        lastBracket = false;
    }
}
