package dev.dluks.rental.model.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "street", nullable = false, length = 200)
    private String street;

    @Column(name = "number", nullable = false, length = 20)
    private String number;

    @Setter
    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "neighborhood", nullable = false, length = 100)
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    public static boolean isNullValue(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isAnyValueNull(Address address) {
        return isNullValue(address.street) ||
                isNullValue(address.number) ||
                isNullValue(address.neighborhood) ||
                isNullValue(address.city) ||
                isNullValue(address.state) ||
                isNullValue(address.zipCode);
    }

    public static Address validateAddressOrThrow(Address address) {
        if (isAnyValueNull(address)) {
            throw new IllegalArgumentException("Broken null value constraint");
        }
        return address;
    }

    public static String validateFieldOrThrow(String field) {
        if (isNullValue(field)) {
            throw new IllegalArgumentException("Broken null value constraint");
        }
        return field;
    }

    public void setStreet(String street) {
        this.street = validateFieldOrThrow(street);
    }

    public void setNumber(String number) {
        this.number = validateFieldOrThrow(number);
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = validateFieldOrThrow(neighborhood);
    }

    public void setCity(String city) {
        this.city = validateFieldOrThrow(city);
    }

    public void setState(String state) {
        this.state = validateFieldOrThrow(state);
    }

    public void setZipCode(String zipCode) {
        this.zipCode = validateFieldOrThrow(zipCode);
    }

    public Address(
            String street, String number, String complement, String neighborhood,
            String city, String state, String zipCode
    ) {
        this.street = validateFieldOrThrow(street);
        this.number = validateFieldOrThrow(number);
        this.complement = complement;
        this.neighborhood = validateFieldOrThrow(neighborhood);
        this.city = validateFieldOrThrow(city);
        this.state = validateFieldOrThrow(state);
        this.zipCode = validateFieldOrThrow(zipCode);
    }

}
