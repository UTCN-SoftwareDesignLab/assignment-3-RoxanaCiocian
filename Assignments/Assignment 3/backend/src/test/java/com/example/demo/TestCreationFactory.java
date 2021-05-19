package com.example.demo;

import com.example.demo.user.model.User;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.dto.PatientDTO;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.dto.ConsultationDTO;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(Patient.class)) {
            supplier = TestCreationFactory::newPatient;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newPatientDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Consultation.class)) {
            supplier = TestCreationFactory::newConsultation;
        } else if (cls.equals(ConsultationDTO.class)) {
            supplier = TestCreationFactory::newConsultationDTO;


        }else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .name(randomString())
                .build();
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    public static LocalDate randomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(1990, 2021);
        return LocalDate.of(year, month, day);
    }

    public static Patient newPatient() {
        return Patient.builder()
                .id(randomLong())
                .firstName(randomString())
                .lastName(randomString())
                .idNumber(randomLong())
                .cnp(randomLong())
                .birthDate(randomDate())
                .address(randomString())
                .build();
    }

    public static PatientDTO newPatientDTO() {
        return PatientDTO.builder()
                .id(randomLong())
                .firstName(randomString())
                .lastName(randomString())
                .idNumber(randomLong())
                .cnp(randomLong())
                .birthDate(randomDate())
                .address(randomString())
                .build();
    }

    public static Consultation newConsultation() {
        return Consultation.builder()
                .id(randomLong())
                .patientId(new Patient())
                .doctorId(new User())
                .description(randomString())
                .build();
    }

    public static ConsultationDTO newConsultationDTO() {
        return ConsultationDTO.builder()
                .id(randomLong())
                .patientId(randomLong())
                .doctorId(randomLong())
                .description(randomString())
                .build();
    }
//    public static LocalDateTime randomLocaDateTime() {
//        int day = createRandomIntBetween(1, 28);
//        int month = createRandomIntBetween(1, 12);
//        int year = createRandomIntBetween(1990, 2021);
//    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static float randomBoundedFloat(float upperBound) {
        return new Random().nextFloat() * upperBound;
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
