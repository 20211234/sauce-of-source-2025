import { API_BASE_URL } from "../app-config";

export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  let options = {
    headers: headers,
    url: API_BASE_URL + api,
    method: method,
  };
  // DELETE와 GET이 아닐 때만 body 추가
  if (request && method !== "GET" && method !== "DELETE") {
    options.body = JSON.stringify(request);
  }

  return fetch(options.url, options)
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        // 200이 아니면 에러로 처리
        return Promise.reject(response);
      }
    })
    .catch((error) => {
      console.log("http error");
      console.log(error);
    });
}
