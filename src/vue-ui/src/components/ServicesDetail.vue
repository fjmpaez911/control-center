<template>
  <v-card
    class="mx-auto"
    max-width="300"
    tile
  >
    <v-list flat>
      <v-subheader>Services</v-subheader>
      <v-list-item-group
        v-model="selectedItem"
        color="primary"
      >
        <v-list-item
          v-for="(item, i) in services"
          :key="i"
        >
          <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="item.mcsv"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>


<script>

  export default {
    name: 'ServicesDetail',

    mounted() {
      this.load()
    },

    data: () => ({
      selectedItem: null,
      services: [],
    }),

    methods: {

      load() {
        
        let es = new EventSource("http://localhost:8080/api/v1/envs/1/details");

        es.addEventListener('message', event => {

          var mcsv = event.data

          if (mcsv.endsWith("-SNAPSHOT")) {
            this.services.push({mcsv, icon: 'mdi-alert'})
          } else {
            this.services.push({mcsv, icon: 'mdi-check'})
          }

        }, false);

        es.addEventListener('open', event => {

          console.log("event", event);
          this.services = []

        }, false);

      },
      
    }

  }
</script>
