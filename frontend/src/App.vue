<template>
  <div id="app">
    <header>
      <nav>
        <router-link :to="`/touren`">Touren</router-link>
      </nav>
    </header>

    <main>
      <div v-if="info" class="infobox">
        <div class="obdacht-bar">Obdacht!</div>
        <p>{{ info }}</p>
        <button @click="clearInfo">x</button>
      </div>

      <button class="btn btn-primary" @click="setInfo('Hallo')">Hallo</button>
      


      <RouterView/>
    
    </main>

    <footer>
      <a href="/licenses">Lizenzen</a>
      <a href="/contact">Kontakt</a>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import TourenListeView from '@/views/TourenListeView.vue';
import { useInfo } from '@/composables/useInfo';

// useInfo einmal aufrufen und Destructuring der gewünschten Teile
const { info, loescheInfo, setzeInfo } = useInfo();

// Methode zum Leeren der Info
const clearInfo = () => {
  loescheInfo();
};

// Methode zum Setzen einer neuen Info-Nachricht
const setInfo = (message: string) => {
  setzeInfo(message);
};
</script>

<style src="./assets/main.css"></style>

<style scoped>

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

header {
  background-color: #4A90E2;
  padding: 10px;
}

nav {
  display: flex;
  align-items: center;
}

nav span {
  font-size: 25px;
  font-weight: bold;
  color: black;
}

main {
  flex: 1;
}

footer {
  background-color: #A9DDF9;
  padding: 10px;
  display: flex;
  justify-content: space-between;
}

.infobox {
 background-color: #ffcccc; 
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ff6666; 
  position: relative;
  color: black;
}

.infobox .obdacht-bar {
  background-color: #ff6666; /* dunkelrot */
  color: black;
  padding: 5px;
  font-weight: bold;
}

.infobox button {
  position: absolute;
  top: 5px;
  right: 5px;
  background: none;
  border: none;
  cursor: pointer;
  color: black;
}
</style>
