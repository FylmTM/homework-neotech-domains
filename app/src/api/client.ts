import { ApiResponse, DomainStatusResponse } from "./domain";

async function getDomainStatus(domainName: string): Promise<DomainStatusResponse> {
  const response = await fetch(`/api/v1/domain/${domainName}/status`);
  const apiResponse: ApiResponse<DomainStatusResponse> = await response.json();

  if (apiResponse.status !== "OK") {
    throw new Error(apiResponse.payload.message)
  }

  return apiResponse.payload
}

export const api = {
  getDomainStatus,
};
