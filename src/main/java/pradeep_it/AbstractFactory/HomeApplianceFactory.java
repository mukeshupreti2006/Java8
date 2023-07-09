package pradeep_it.AbstractFactory;

public class HomeApplianceFactory extends ProductFactory {
    @Override
    public Product createElectronicProduct() {
        return new Television();
    }

    @Override
    public Product createClothingProduct() {
      return  new NullProduct();
    }
}

