<template>
  <footer>
    &copy; 2023. All rights reserved. <font-awesome-icon class="paw" icon="fa-solid fa-paw" />
  </footer>
</template>
  
<script>
  export default {
    name: 'AppFooter'
  };
</script>

<style scoped>
  footer {
    grid-area: footer;
    border-top: 2px solid black;
    background-color: rgb(243, 206, 206);
    opacity: 90%;
  }

  .paw {
    color: #3f87a6;
  }
</style>