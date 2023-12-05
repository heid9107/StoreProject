<template>
    <div class="individualProduct">
        <h2> {{ productProp.name }}</h2>
        <h2> {{ productProp.description }}</h2>
        <h2> {{ productProp.productSKU }} ${{ productProp.price }}</h2>
        <p> {{ productProp.name }}</p>
        <div class="image">
            <img class="product-image" src="https://via.placeholder.com/350x250.jpg" />
        </div>
    </div>
</template>
  
<script>
  export default {
    props: {
      productProp: Object
    },
  };
</script>
  
<style scoped>
.individualProduct {
    display: flex;
    flex-direction: column;
}

h2 {
    display: flex;
    justify-content: center;
}

h2:first-child {
  font-size: 25px;
}

p {
  display: flex;
  justify-content: center;
}

img {
  border-radius: 15%;
  margin-bottom: 20px;
  height: 250px;
  width: 350px;
}
</style>