package pl.javastart.restassured.main.test.data;

import pl.javastart.restassured.main.pojo.pet.Category;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.pojo.pet.Tag;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.main.test.data.pet.PetsCategory;
import pl.javastart.restassured.main.test.data.pet.PetsTags;

import java.util.Collections;
import java.util.Random;

public class PetTestDataGenerator extends TestDataGenerator {



    public Pet generatePet(){
        Random random = new Random();

        Category category = new Category();
        int randomNumber = random.nextInt(PetsCategory.values().length);
        PetsCategory petsCategory = PetsCategory.values()[randomNumber];
        category.setId(petsCategory.getValue());
        category.setName(petsCategory.getDescription());

        Tag tag = new Tag();
        randomNumber = random.nextInt(PetsTags.values().length);
        PetsTags petsTags = PetsTags.values()[randomNumber];
        tag.setId(petsTags.getValue());
        tag.setName(petsTags.getDescription());

        randomNumber = random.nextInt(PetStatus.values().length);
        PetStatus petStatus = PetStatus.values()[randomNumber];
        String status = petStatus.getDescription();

        Pet pet = new Pet();
        pet.setName(faker().funnyName().name());
        pet.setId(faker().number().numberBetween(1,9999));
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList(faker().internet().url()));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus(status);

        return pet;
    }

}
