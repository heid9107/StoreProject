import axios from 'axios';

export default {
    getAllProducts() {
        return axios.get('/products')
    },

    getProductBySKU(sku) {
        return axios.get(`/products?sku=${sku}`)
    },

    getProductByID(id) {
        return axios.get(`/products/${id}`)
    },

    getProductByName(name) {
        return axios.get(`/products?name=${name}`)
    },

    searchProducts(searchTerm) {
        return axios.get(`/products?name=${searchTerm}`);
    }
}