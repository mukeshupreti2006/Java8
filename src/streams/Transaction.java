package streams;

public class Transaction {



    private Integer id;
    private Integer value;
    private String city;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public Transaction(Integer id, Integer value, String city,String currency) {
        this.id = id;
        this.value = value;
        this.city=city;
        this.currency=currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    @Override
    public String  toString() {
        return "Transaction{" +
                "id=" + id +
                ", value=" + value +
                ", city='" + city + '\'' +
                '}';
    }


}
