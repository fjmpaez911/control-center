<template>
  <v-container>

    <v-row justify="center">
      <v-expansion-panels inset multiple>
        
        <v-expansion-panel
          v-for="(environment, i) in environments"
          :key="i"
        >
          
          <v-expansion-panel-header disable-icon-rotate>
            <span>
              {{ environment.branch }}
            </span> 
            {{ environment.owner }}

            <template v-slot:actions>
              <v-icon v-if="i==2" color="red" class="ml-10">
                mdi-alert
              </v-icon>
              <v-icon v-else color="teal" class="ml-10">
                mdi-check
              </v-icon>
            </template>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <ServicesDetail/>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-row>
    
  </v-container>
</template>


<script>
  import ServicesDetail from '@/components/ServicesDetail';

  export default {
    name: 'Home',

    components: {
      ServicesDetail,
    },

    mounted() {
      this.load()
    },

    data: () => ({
      environments: [],
    }),

    methods: {

      load() {
        
        let es = new EventSource("http://localhost:8080/api/v1/envs");

        es.addEventListener('message', event => {

          var env = JSON.parse(event.data)

          const index = this.environments.findIndex((e) => e.branch === env.branch);

          if (index === -1) {
              this.environments.push(env)
          } else {
              this.environments[index] = env
          }


        }, false);


        es.addEventListener('open', event => {

          console.log("event", event)

        }, false);

      },
      
    }

  }
</script>
