package com.mobiquity.dto;

import java.util.Objects;

/**
 * Class keeps all information of the things.
 * Only way to instantiate the class is using the builder {@link ThingDTOBuilder}.
 */
public class ThingDTO {

    private final Integer id;
    private final Double weight;
    private final Integer price;
    private final String currency;

    private ThingDTO(Integer id, Double weight, Integer price, String currency) {
        this.id = id;
        this.weight = weight;
        this.price = price;
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThingDTO that = (ThingDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ThingDTO{" +
                "id=" + id +
                ", weight=" + weight +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }

    /**
     * Builder class for {@link ThingDTO}
     */
    public static class ThingDTOBuilder {
        private Integer id;
        private double weight;
        private Integer price;
        private String currency;

        private void id(final String id) {
            this.id = Integer.parseInt(id);
        }

        private void weight(final String weight) {
            this.weight = Double.parseDouble(weight);
        }

        // Parse price into currency and amount
        private void price(final String price) {
            final char[] chars = price.toCharArray();
            int pos = 0;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isDigit(c)) {
                    pos = i;
                    break;
                }
            }

            this.price = Integer.parseInt(price.substring(pos));
            this.currency = price.substring(0, pos);
        }

        public ThingDTO build(final String[] thingDetails) {
            id(thingDetails[0].trim());
            weight(thingDetails[1].trim());
            price(thingDetails[2].trim());
            return new ThingDTO(id, weight, price, currency);
        }
    }
}
