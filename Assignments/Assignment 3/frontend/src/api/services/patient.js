import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPatients() {
    return HTTP.get(BASE_URL + "/patients", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createPatient(patient) {
    return HTTP.post(BASE_URL + "/patients", patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editPatient(id, patient) {
    return HTTP.put(BASE_URL + "/patients/" + id, patient, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
