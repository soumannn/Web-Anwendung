<template>
    <tr>
      <td>{{ tour.anbieterName }}</td>
      <td>{{ tour.startOrtName }}</td>
      <td>{{ tour.zielOrtName }}</td>
      <td>{{ new Date(tour.abfahrDateTime).toLocaleString() }}</td>
      <td>{{ tour.preis }} €</td>
      <td>{{ tour.plaetze }}</td>
      <td>{{ tour.plaetze - tour.buchungen }}</td>
      <td>
        <router-link :to="`/tour/${tour.id}`">Details</router-link>
      </td>
    </tr>
  </template>
  
  <script setup lang ="ts">
  import { type PropType } from 'vue'
  import type { ITourDTD } from '@/stores/ITourDTD';
  //import type { ITourDTD } from '@/views/TourenListeView.vue'
  
  defineProps({
    tour: {
      type: Object as PropType<ITourDTD>,
      required: true
    }
  })
  </script>
  
  <style scoped></style>
  