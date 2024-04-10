class Address {
    provinceOrCity: string;
    district: string;
    wards: string;
    detailsAddress: string;

    constructor(provinceOrCity: string, district: string, wards: string, detailsAddress: string) {
        this.provinceOrCity = provinceOrCity;
        this.district = district;
        this.wards = wards;
        this.detailsAddress = detailsAddress;
    }
}

export default Address;
