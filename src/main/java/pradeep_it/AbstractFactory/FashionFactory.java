package pradeep_it.AbstractFactory;

// Concrete Factory
public class FashionFactory extends ProductFactory {
    @Override
    public Product   createElectronicProduct() {
        return new NullProduct();
    }

    @Override
    public Product createClothingProduct() {
        return new Shirt();
    }
}