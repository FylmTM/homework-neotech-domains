// @ts-nocheck
const $form = document.getElementById("getStatusForm");
const $domainName = document.getElementById("domainName") as HTMLInputElement;
const $result = document.getElementById("result");

$form.onsubmit = async (e) => {
  e.preventDefault();

  const domainName = $domainName.value;

  if (!domainName) {
    showError({
      code: "bad_request",
      message: "Domain name is empty",
    });
    return;
  }

  try {
    const response = await getDomainStatus($domainName.value);
    switch (response.status) {
      case "OK":
        showResponse(response.payload);
        break;
      case "ERROR":
        showError(response.payload);
        break;
    }
  } catch (e) {
    showError({
      code: "unknown",
      message: e.toString(),
    });
  }
};

async function getDomainStatus(domainName: string): Promise<ApiResponse<DomainStatusResponse>> {
  const response = await fetch(`/api/v1/domain/${domainName}/status`);
  return await response.json();
}

function showResponse({ domain, status }: DomainStatusResponse) {
  const domainHTML = `
    <b>Domain:</b>
    <ul>
      <li>Original: ${domain.original}</li>
      <li>Normalized: ${domain.normalized}</li>
    </ul>
  `;

  let informationHTML;
  switch (status.status) {
    case "registered":
      informationHTML = `
      <b>Information:</b>
      <ul>
        <li>Status: ${status.status}</li>
        <li>Registrar: ${status.registrar}</li>
        <li>Expires: ${status.expirationDate}</li>
      </ul>
      `;
      break;
    case "available":
      const showPrice = (price: DomainPrice | undefined) => {
        if (price == null) {
          return "-";
        } else {
          return `${price.amount} ${price.currency}`;
        }
      };
      informationHTML = `
      <b>Information:</b>
      <ul>
        <li>Status: ${status.status}</li>
      </ul>
      <br/>
      <b>Prices:</b>
      <ul>
        ${
        status.prices.map((price) => {
          return `<li>${price.registrar}: ${price.available ? showPrice(price.price) : "-"}</li>`;
        })
      }
      </ul>
      `;
      break;

  }

  $result.classList.remove("is-danger");
  $result.innerHTML = `
    ${domainHTML}
    <br/>
    ${informationHTML}
  `;
}

function showError(response: ErrorResponse) {
  const content = `
    <b>Error!</b><br/>
    <br/>
    <b>Code:</b> ${response.code}<br/>
    <b>Message:</b> ${response.message}<br/>
  `.trim();

  $result.classList.add("is-danger");
  $result.innerHTML = content;
}

type ApiResponse<T> =
  | { status: "OK", payload: T }
  | { status: "ERROR", payload: ErrorResponse }

interface ErrorResponse {
  code: string;
  message: string;
}

interface DomainStatusResponse {
  domain: Domain;
  status: DomainStatus;
}

interface Domain {
  original: string;
  normalized: string;
}

type DomainStatus =
  | RegisteredDomain
  | AvailableDomain

interface RegisteredDomain {
  status: "registered";
  registrar: string;
  expirationDate: string;
}

interface AvailableDomain {
  status: "available";
  prices: Array<{
    registrar: string;
    available: boolean;
    price: DomainPrice | undefined;
  }>
}

interface DomainPrice {
  amount: string;
  currency: string;
}
