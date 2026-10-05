<template>
    <table class="table">
      <thead>
        <tr>
          <th>Anbieter</th>
          <th>Startort</th>
          <th>Zielort</th>
          <th>Abfahrt</th>
          <th>Preis</th>
          <th>Plätze</th>
          <th>Frei</th>
          <th>Aktionen</th>
        </tr>
      </thead>
      <tbody>
        <!-- Iteriert über die Tourdaten und erstellt für jede Tour eine Zeile -->
        <TourListeZeile v-for="tour in touren" :key="tour.id" :tour="tour" />
      </tbody>
    </table>
  </template>
  
  <script setup lang="ts">
  import TourListeZeile from './TourListeZeile.vue';
  import type { ITourDTD } from '@/stores/ITourDTD';
  //import type { ITourDTD } from '@/views/TourenListeView.vue';
  
  // Props definieren für die Komponenete
  const props = defineProps<{
    touren: ITourDTD[]
  }>();
  </script>
  
  <style scoped></style>
  