package pl.pr0gramista.enums;

public enum Color {
    RED("FF0000"), GREEN("00FF00"), BLUE("0000FF");

    private String hex;

    Color(String hex) {
        this.hex = hex;
    }
}
