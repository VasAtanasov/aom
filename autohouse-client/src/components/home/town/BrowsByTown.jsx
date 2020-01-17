import React from 'react';
import styled from 'styled-components';
import { Col } from 'antd';
import TownCard from './TownCard';

const TownCardContainer = styled(Col)`

    & .ant-card {
        margin: 10px !important;
        cursor: pointer;

        & .ant-card-body {
            padding: 0;
        }

        & .ant-card-cover {

            & .city-card {

                & .preloader {
                    display: none;
                }

                & .city-card-image-container {
                    display: block;
                    background-size: cover;

                    & .city-card-title {
                        margin: 0;
                        min-width: 100%px;
                        height: 90px;
                        background-color: rgba(0, 0, 0, 0.6);
                        text-align: center;
                        line-height: 72px;
                        letter-spacing: 0.2px;
                        font-size: 14px;
                        color: white;
                        transition: 0.3s;

                        :hover {
                            background-color: rgba(0, 0, 0, 0.1);
                        }
                    }
                }
            }
        }
    }
`;

const mapName = function (str) {
    return str.replace(/\s+/g, '_').toLowerCase()
};

const BrowsByTown = () => {
    const pathToImages = '/images/city-photos/';
    const towns = {
        "Sofia": "https://www.dropbox.com/s/7mex3gcs8il29w6/sofia.jpg?dl=0&raw=1",
        "Plovdiv": "https://www.dropbox.com/s/uiemhvc9xmhh5p5/plovdiv.jpg?dl=0&raw=1",
        "Varna": "https://www.dropbox.com/s/bnzexmprdqs9q5j/varna.jpg?dl=0&raw=1",
        "Burgas": "https://www.dropbox.com/s/88pxwwypmbq6okq/burgas.jpg?dl=0&raw=1",
        "Ruse": "https://www.dropbox.com/s/63fxvbi4t40mnhp/ruse.jpg?dl=0&raw=1",
        "Stara Zagora": "https://www.dropbox.com/s/65ltxeynzxhvxji/stara_zagora.jpg?dl=0&raw=1",
        "Pleven": "https://www.dropbox.com/s/14ol4tg8o4hsru8/pleven.jpg?dl=0&raw=1",
        "Sliven": "https://www.dropbox.com/s/srdit8aw4cgwkxv/sliven.jpg?dl=0&raw=1",
        "Dobrich": "https://www.dropbox.com/s/z07ilixl7pk393z/dobrich.jpg?dl=0&raw=1",
        "Shumen": "https://www.dropbox.com/s/asl2e8mwb27vqb5/shumen.jpg?dl=0&raw=1",
        "Pernik": "https://www.dropbox.com/s/bvro73sabep9yo8/pernik.jpg?dl=0&raw=1",
        "Haskovo": "https://www.dropbox.com/s/6rj9qw09nmjeoej/haskovo.jpg?dl=0&raw=1",
        "Yambol": "https://www.dropbox.com/s/gptg5dgfpdoilvv/yambol.jpg?dl=0&raw=1",
        "Pazardjhik": "https://www.dropbox.com/s/n64p897kigyny0f/pazardjhik.jpg?dl=0&raw=1",
        "Blagoevgrad": "https://www.dropbox.com/s/c4zm60n96ydigbv/blagoevgrad.jpg?dl=0&raw=1",
        "Veliko Tarnovo": "https://www.dropbox.com/s/hyxw7zmwxk7iu3w/veliko_tarnovo.jpg?dl=0&raw=1",
        "Vratsa": "https://www.dropbox.com/s/zmb7skmxv4smjnq/vratsa.jpg?dl=0&raw=1",
        "Gabrovo": "https://www.dropbox.com/s/uniocmnqjipniip/gabrovo.jpg?dl=0&raw=1",
        "Asenovgrad": "https://www.dropbox.com/s/ad5e0ubfrmil6mz/asenovgrad.jpg?dl=0&raw=1",
        "Vidin": "https://www.dropbox.com/s/j81ovfscg4kvanv/vidin.jpg?dl=0&raw=1",
    };
    const extensionJpg = ".jpg";

    return Object.keys(towns).map((town, idx) => (
        <TownCardContainer
            key={town + idx}
            span={12}
            md={8}
            lg={6}
        >
            <TownCard
                townName={town}
                imageSrc={`${pathToImages}${mapName(town)}${extensionJpg}`}
            />
        </TownCardContainer>
    ))
}

export default BrowsByTown;