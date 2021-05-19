import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsultations() {
    return HTTP.get(BASE_URL + "/consultations", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createConsultation(consultation) {
    return HTTP.post(BASE_URL + "/consultations", consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editConsultation(id, consultation) {
    return HTTP.put(BASE_URL + "/consultations/" + id, consultation, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  editDescription(id, consultation) {
    return HTTP.put(
      BASE_URL + "/consultations/" + id + "/description",
      consultation,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  patientArrived() {
    return HTTP.post(
      BASE_URL + "/doctor/patientArrived",
      {},
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
  deleteConsultation(id) {
    return HTTP.delete(BASE_URL + "/consultations/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
