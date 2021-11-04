package cn.demo.boot.spi.exception;

public class NoSpiChooseException extends RuntimeException {

    public NoSpiChooseException(String text) {
        super(text);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
