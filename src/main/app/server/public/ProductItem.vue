<!-- ProductItem.vue -->
<template>
  <a :href="'/glasses/' + eyewear.id" class="product-item">
    <img :src="eyewear.image_url" alt="Product Image">
    <div class="product-details">
      <b>id</b>: {{ eyewear.id }}<br>
      <b>name</b>: {{ eyewear.name }}<br>
      <b>brand</b>: {{ eyewear.brand }}<br>
      <b>price</b>: {{ eyewear.price }}
    </div>
  </a>
</template>

<script>
export default {
  props: {
    eyewear: Object
  }
};
</script>

<style src="css/style.css"></style>

