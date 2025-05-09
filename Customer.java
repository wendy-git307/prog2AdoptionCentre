package model.Users;

import model.Animals.*;
import model.Exceptions.InvalidOperationException;


public class Customer extends User {
    private final String email;
    private static final int ADOPTION_LIMIT = 2;
    private final Animals adoptedAnimals;



    public Customer(String name, String email) {
        super(name);
        this.email = email;
        this.adoptedAnimals = new Animals();
    }

    public String getEmail() {
        return email;
    }

    public Animals adoptedAnimals() {
        return adoptedAnimals;
    }
    // helper method to add adopted animals and throw exception if reaching the adoption limit
    public void adoptAnimal(Animal animal) throws InvalidOperationException {
        if(canAdopt(animal)) {
            adoptedAnimals.add(animal);
        }
        else {
            throw new InvalidOperationException("Cannot adopt " + animal.getName() + ", adoption limit for " + animal.getClass().getSimpleName() + "s reached"); 
        }
    }

    public boolean canAdopt(Animal animal) {
        int count = 0;
        for (Animal a : adoptedAnimals.getAnimals()) {
            if (a.getClass().equals(animal.getClass())) {
                count++;
            }
        }
        return count < ADOPTION_LIMIT;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.email + ")";
    }
}