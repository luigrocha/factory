import { HttpResponse } from '@angular/common/http';

export function getFileFromResponse(response: HttpResponse<Blob>) {
  const type = response.body.type;
  const a = document.createElement('a');
  const generatedUrl = URL.createObjectURL(
    new Blob([response.body], {type})
  );
  a.href = generatedUrl;
  a.download = response.headers
    .get('content-disposition')
    .split('filename=')[1];
  document.body.appendChild(a);
  a.click();
  setTimeout(() => {
    document.body.removeChild(a);
    window.URL.revokeObjectURL(generatedUrl);
  }, 0);
  return response;
}
