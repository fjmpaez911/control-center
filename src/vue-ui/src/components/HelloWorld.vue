<template>
  <v-container>

    <v-row justify="center">
      <v-expansion-panels inset multiple>
        
        <v-expansion-panel
          v-for="(environment,i) in environments"
          :key="i"
        >
          
          <v-expansion-panel-header disable-icon-rotate>
            <span>
              {{ environment.branch }}
            </span> 
            {{ environment.owner }}

            <template v-slot:actions>
              <v-icon color="teal" class="ml-10">
                mdi-check
              </v-icon>
            </template>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            {{ environment.services }}
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-row>
    
  </v-container>
</template>


<script>
  import controlCenterService from '@/services/ControlCenterService.js'

  export default {
    name: 'HelloWorld',

    created() {
      this.load()
    },

    data: () => ({
      environments: [],
    }),

    methods: {

      load() {
        controlCenterService.environments.getAll().then(it => this.environments = it.data)
      },

    }

  }
</script>
