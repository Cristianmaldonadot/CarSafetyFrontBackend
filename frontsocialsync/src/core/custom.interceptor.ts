import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {

  const token = localStorage.getItem('token');
  const apiUrl = 'https://socialsyncc.azurewebsites.net'
  const cloneRequest = req.clone({
    url: apiUrl+req.url,
    setHeaders:{
      Authorization:`Bearer ${token}`
    }
  })
  return next(cloneRequest);
};
