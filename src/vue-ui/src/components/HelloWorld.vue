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
  export default {
    name: 'HelloWorld',

    data: () => ({
      environments: [
        {
          branch: 'env1',
          owner: 'Colin Ford',
          services: ['mcsv1', 'mcsv2']
        },
        {
          branch: 'env2',
          owner: 'James Rusel',
          services: ['mcsv1', 'mcsv2', 'mcsv3']
        },
        {
          branch: 'env3',
          owner: 'Tim Anderson',
          services: ['mcsv1', 'mcsv5']
        },
      ],
    }),
  }
</script>
