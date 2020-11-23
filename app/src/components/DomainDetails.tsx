import { Domain } from "../api/domain";
import React, { FC } from "react";
import { Card, H3 } from "@blueprintjs/core";

interface Props {
  domain: Domain;
}

export const DomainDetails: FC<Props> = ({ domain }) => {
  return (
    <Card className="panel">
      <H3>Domain</H3>
      <div>
        <b>Original:</b> <code>{domain.original}</code>
      </div>
      <div>
        <b>Normalized:</b> <code>{domain.normalized}</code>
      </div>
    </Card>
  );
};
