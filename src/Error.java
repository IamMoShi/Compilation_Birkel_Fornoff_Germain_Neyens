public class Error {
    private String name;
    private String details;

    public Error(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return this.name;
    }

    public String getDetails() {
        return this.details;
    }
}
