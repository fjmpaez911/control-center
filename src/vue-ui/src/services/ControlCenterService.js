import axios from "axios";
import { commonsService } from "@/services/CommonsService.js";

const baseURL = commonsService.getControlCenterBasePath();
const service = axios.create({ baseURL });

export default {
  
  environments: {
    getAll() {
      return service.get("/envs/block");
    },
    create(data) {
      return service.post("/envs", data);
    },
    get(env) {
      return service.get("/envs/" + env + "/details");
    },
    update(env, data) {
      return service.put("/envs/" + env , data);
    },
    delete(env) {
      return service.delete("/envs/" + env );
    }
  },

  admin: {
    getInfraUrls() {
      return service.get("/admin/infra-urls");
    }
  }

};
