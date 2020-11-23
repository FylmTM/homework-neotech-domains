export type ApiResponse<T> =
  | { status: "OK", payload: T }
  | { status: "ERROR", payload: ErrorResponse }

export interface ErrorResponse {
  code: string;
  message: string;
}

export interface DomainStatusResponse {
  domain: Domain;
  status: DomainStatus;
}

export interface Domain {
  original: string;
  normalized: string;
}

export type DomainStatus =
  | RegisteredDomain
  | AvailableDomain

export interface RegisteredDomain {
  status: "registered";
  registrar: string | undefined;
  expirationDate: string | undefined;
}

export interface AvailableDomain {
  status: "available";
  prices: Array<{
    registrar: string;
    available: boolean;
    price: DomainPrice | undefined;
  }>
}

export interface DomainPrice {
  amount: string;
  currency: string;
}
