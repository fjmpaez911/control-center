
const schema = "http";
const host = "localhost";

const port = "8080";


export const commonsService = {
  getControlCenterBasePath() {
    return getBasePath(port) + "/api/v1";
  }
};


function getBasePath(port) {
  if (port === 0) {
    return schema + "://" + host;
  } else {
    return schema + "://" + host + ":" + port;
  }
}
