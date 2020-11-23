import { AvailableDomain, DomainPrice, DomainStatus, RegisteredDomain } from "../api/domain";
import React, { FC } from "react";
import { Card, H3, H4 } from "@blueprintjs/core";

interface Props {
  domainStatus: DomainStatus;
}

export const DomainStatusDetails: FC<Props> = ({ domainStatus }) => {
  switch (domainStatus.status) {
    case "registered":
      return <RegisteredDomainDetails domainStatus={domainStatus} />;
    case "available":
      return <AvailableDomainDetails domainStatus={domainStatus} />;
  }
};

interface RegisteredProps {
  domainStatus: RegisteredDomain;
}

const RegisteredDomainDetails: FC<RegisteredProps> = ({ domainStatus }) => {
  return (
    <Card className="panel">
      <H3>Information</H3>
      <div>
        <b>Status:</b> <code>{domainStatus.status}</code>
      </div>
      <div>
        <b>Registrar:</b> <code>{domainStatus.registrar}</code>
      </div>
      <div>
        <b>Expires:</b> <code>{domainStatus.expirationDate}</code>
      </div>
    </Card>
  );
};

interface AvailableProps {
  domainStatus: AvailableDomain;
}

const AvailableDomainDetails: FC<AvailableProps> = ({ domainStatus }) => {
  return (
    <Card className="panel">
      <H3>Information</H3>
      <div>
        <b>Status:</b> <code>{domainStatus.status}</code>
      </div>
      <br/>
      <H4>Prices</H4>
      {domainStatus.prices.map((data) => (
        <DomainPriceDetails
          registrar={data.registrar}
          available={data.available}
          price={data.price}
        />
      ))}
    </Card>
  );
};

interface PriceProps {
  registrar: string;
  available: boolean;
  price: DomainPrice | undefined;
}

const DomainPriceDetails: FC<PriceProps> = ({ registrar, available, price }) => {
  const priceIfAvailable = available && price ? `${price.amount} ${price.currency}` : "-";
  return (
    <div>
      <b>{registrar}</b>: {priceIfAvailable}
    </div>
  );
};
