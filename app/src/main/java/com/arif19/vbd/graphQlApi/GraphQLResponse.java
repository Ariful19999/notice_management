package com.arif19.vbd.graphQlApi;

import java.util.List;

public class GraphQLResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private List<Country> countries;

        public List<Country> getCountries() {
            return countries;
        }

        public class Country {
            private String name;
            private List<Currency> currencies;
            private String awsRegion;
            private String phone;

            public String getName() {
                return name;
            }

            public List<Currency> getCurrencies() {
                return currencies;
            }

            public String getAwsRegion() {
                return awsRegion;
            }

            public String getPhone() {
                return phone;
            }

            // Inner class for Currency
            public class Currency {
                private String code;

                public String getCode() {
                    return code;
                }
            }
        }
    }
}
